package kodlama.io.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByIdBrandReponse;
import kodlama.io.rentACar.business.rules.BrandBusinessRules;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.BrandRepository;
import kodlama.io.rentACar.entities.concretes.Brand;

@Service // Bu sınıf bir business nesnesidir.
public class BrandManager implements BrandService {

	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	private BrandBusinessRules brandBusinessRules;

	@Autowired
	public BrandManager(BrandRepository brandRepository, ModelMapperService modelMapperService,
			BrandBusinessRules brandBusinessRules) {
		this.brandRepository = brandRepository;
		this.modelMapperService = modelMapperService;
		this.brandBusinessRules = brandBusinessRules;
	}

	@Override
	public List<GetAllBrandsResponse> getAll() {
		List<Brand> brands = brandRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		List<GetAllBrandsResponse> brandsResponse = brands.stream()
				.map(brand -> this.modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class))
				.collect(Collectors.toList());
		// İş Kuralları
		return brandsResponse;
	}

//	@Override
//	public List<GetAllBrandsResponse> getAll() {
//		List<Brand> brands = brandRepository.findAll();
//		List<GetAllBrandsResponse> brandsResponse = new ArrayList<GetAllBrandsResponse>();
//		for (Brand brand : brands) {
//			GetAllBrandsResponse responseItem = new GetAllBrandsResponse();
//			responseItem.setId(brand.getId());
//			responseItem.setName(brand.getName());
//			brandsResponse.add(responseItem);
//		}
//		// İş Kuralları
//		return brandsResponse;
//	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) {
		this.brandBusinessRules.checkIfBrandNameExists(createBrandRequest.getName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandRepository.save(brand);
	}

//	@Override
//	public void add(CreateBrandRequest createBrandRequest) {
//		Brand brand = new Brand();
//		brand.setName(createBrandRequest.getName());
//		this.brandRepository.save(brand);
//	}

	@Override
	public GetByIdBrandReponse getById(int id) {
		Brand brand = this.brandRepository.findById(id).orElseThrow();
		GetByIdBrandReponse response = this.modelMapperService.forResponse().map(brand, GetByIdBrandReponse.class);
		return response;
	}

	@Override
	public void update(UpdateBrandRequest updateBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandRepository.save(brand);
		// Hem add isleminde hem de update isleminde brandRepository de bulunan save i
		// kullandik. add isleminde elimizde herhangi bir id olmadigi icin INSERT islemi
		// yaparken update isleminde id yi aldigimiz icin repository bunu farkeder ve
		// update eder.
	}

	@Override
	public void delete(int id) {
		this.brandRepository.deleteById(id);

	}

}
