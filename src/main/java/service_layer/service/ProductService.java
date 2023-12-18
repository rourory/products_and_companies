package main.java.service_layer.service;

import main.java.data_access_layer.dto.CategoryDTO;
import main.java.data_access_layer.dto.CompanyDTO;
import main.java.data_access_layer.dto.CountryDTO;
import main.java.data_access_layer.dto.ProductDTO;
import main.java.data_access_layer.entity.Category;
import main.java.data_access_layer.entity.Company;
import main.java.data_access_layer.entity.Country;
import main.java.data_access_layer.entity.Product;
import main.java.data_access_layer.repository.CategoryRepository;
import main.java.data_access_layer.repository.CompanyRepository;
import main.java.data_access_layer.repository.CountryRepository;
import main.java.data_access_layer.repository.ProductRepository;
import main.java.service_layer.mapper.CategoryMapper;
import main.java.service_layer.mapper.CompanyMapper;
import main.java.service_layer.mapper.CountryMapper;
import main.java.service_layer.mapper.ProductMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService implements IService<ProductDTO, Long> {
    private final ProductRepository productRepository = new ProductRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();
    private final CountryRepository countryRepository = new CountryRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final ProductMapper productMapper = new ProductMapper();
    private final CompanyMapper companyMapper = new CompanyMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final CountryMapper countryMapper = new CountryMapper();

    /**
     * Возвращает список всех продуктов из БД в виде DTO.
     * DTO получаются путем преобразования entity объектов
     */
    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return find(products);
    }

    /**
     * Возвращает список всех продуктов из БД в виде DTO.
     * Выборка осуществляется по id компании.
     * DTO получаются путем преобразования entity объектов
     */
    public List<ProductDTO> findAllByCompanyId(Long companyId) {
        List<Product> products = productRepository.findAllByParameterID(companyId, "company_id");
        return find(products);
    }

    /**
     * Просто вынесенная в отдельный метод логика.
     *
     * ВАЖНО!!!
     * Если пройтись по методу, то станет понятно, что для каждого продукта делается запрос в базу данных для получения компании и категории.
     * В жизни так писть код - это просто кощунство. Чем меньше запросов в базу и чем они проще, тем лучше.
     * Hibernate все это может вытянуть за три - четыре запроса в независимости от количества прокутов.
     */
    private List<ProductDTO> find(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        // Для каждого продукта нужно найти компанию и категорию по его вторичным ключам
        for (Product p : products) {
            Optional<Company> company = companyRepository.findOneById(p.getCompanyId());
            Optional<Category> category = categoryRepository.findOneById(p.getCategoryId());
            // Категория или компания может быть не найдена, поэтому делаем проверку и устанавливаем null, если ничего не найдено
            if (category.isPresent() && company.isPresent()) {
                Optional<Country> country = countryRepository.findOneById(company.get().getCountryId());
                // У компании есть страна, поэтому и тут нужно сделать проверку
                if (country.isPresent())
                    dtos.add(productMapper.map(p, companyMapper.map(
                                    company.get(),
                                    countryMapper.map(country.get())),
                            categoryMapper.map(category.get())));
                else
                    dtos.add(productMapper.map(p, companyMapper.map(
                                    company.get(),
                                    null),
                            categoryMapper.map(category.get())));
            } else {
                dtos.add(productMapper.map(p, null, null));
            }
        }
        return dtos;
    }

    @Override
    public ProductDTO findOneById(Long id) {
        ProductDTO productDTO = new ProductDTO();
        CompanyDTO companyDTO = new CompanyDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        Optional<Product> product = productRepository.findOneById(id);
        if (product.isPresent()) {
            Optional<Category> category = categoryRepository.findOneById(product.get().getCategoryId());
            Optional<Company> company = companyRepository.findOneById(product.get().getCompanyId());
            categoryDTO = category.isPresent() ? categoryMapper.map(category.get()) : null;
            if (company.isPresent()) {
                Optional<Country> country = countryRepository.findOneById(company.get().getCountryId());
                CountryDTO countryDTO = country.isPresent() ? countryMapper.map(country.get()) : null;
                companyDTO = companyMapper.map(company.get(), countryDTO);
            }
            productDTO = productMapper.map(product.get(), companyDTO, categoryDTO);
        } else {
            return null;
        }
        return productDTO;
    }

    @Override
    public ProductDTO save(ProductDTO entity) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Product p = productRepository.save(productMapper.remap(entity, now, now));
        Optional<Company> company = companyRepository.findOneById(p.getCompanyId());
        Optional<Category> category = categoryRepository.findOneById(p.getCategoryId());
        CountryDTO countryDTO = null;
        CompanyDTO companyDTO = null;
        if (company.isPresent()) {
            Optional<Country> country = countryRepository.findOneById(company.get().getCountryId());
            countryDTO = country.isPresent() ? countryMapper.map(country.get()) : null;
            companyDTO = companyMapper.map(company.get(), countryDTO);
        }
        CategoryDTO categoryDTO = category.isPresent() ? categoryMapper.map(category.get()) : null;
        return productMapper.map(p, companyDTO, categoryDTO);
    }

    @Override
    public ProductDTO update(ProductDTO entity) {
        CompanyDTO companyDTO = null;
        CountryDTO countryDTO = null;
        CategoryDTO categoryDTO = null;
        Optional<Product> productById = productRepository.findOneById(entity.getId());
        if (productById.isPresent()) {
            Product product = productById.get();
            product = productRepository.update(productMapper.remap(entity, product.getCreatedAt(), null));
            Optional<Category> category = categoryRepository.findOneById(product.getCategoryId());
            categoryDTO = category.isPresent() ? categoryMapper.map(category.get()) : null;
            Optional<Company> company = companyRepository.findOneById(product.getCompanyId());
            if (company.isPresent()) {
                Optional<Country> country = countryRepository.findOneById(company.get().getCountryId());
                countryDTO = country.isPresent() ? countryMapper.map(country.get()) : null;
                companyDTO = companyMapper.map(company.get(), countryDTO);
            }
            return productMapper.map(product, companyDTO, categoryDTO);
        }
        return entity;
    }

    @Override
    public Boolean removeById(Long id) {
        return productRepository.removeById(id);
    }
}
