package kodlama.io.rentACar.webApi.controllers;

import kodlama.io.rentACar.business.concretes.BrandManager;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByIdBrandResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandsControllerTest {

    @InjectMocks
    private BrandsController brandsController;

    @Mock
    private BrandManager brandManager;

    @Test
    void getById() {
        GetByIdBrandResponse response = brandsController.getById(1);
        assertNull(response);
    }

    @Test
    void getAll() {
        List<GetAllBrandsResponse> responses = brandsController.getAll();
        assertNotNull(responses);
    }

    @Test
    void add() {
        CreateBrandRequest request = mock(CreateBrandRequest.class);
        request.setName("test-name");
        brandsController.add(request);
        verify(brandManager, times(1)).add(request);
    }

    @Test
    void update() {
        UpdateBrandRequest updateBrandRequest = mock(UpdateBrandRequest.class);
        updateBrandRequest.setId(1);
        updateBrandRequest.setName("test-name");
        brandsController.update(updateBrandRequest);
        verify(brandManager, times(1)).update(updateBrandRequest);
    }

    @Test
    void delete() {
        brandsController.delete(1);
        verify(brandManager, times(1)).delete(1);
    }
}