
package com.driver.service.impl;
import com.driver.io.converter.FoodConverter;
import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = FoodConverter.convertDtoToEntity(food);
        foodEntity.setFoodId(getAlphaNumericString(15));
        foodEntity = foodRepository.save(foodEntity);

        FoodDto newFoodDto = FoodConverter.convertEntityToDto(foodEntity);

        return newFoodDto;
    }

    // function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if(foodEntity == null) throw new Exception("Food doesn't exist!!!");

        FoodDto foodDto = FoodConverter.convertEntityToDto(foodEntity);
        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        long id = foodEntity.getId();
        if(foodEntity == null) throw new Exception("Food doesn't exist!!!");

        foodEntity = FoodEntity.builder()
                .id(id)
                .foodName(foodDetails.getFoodName())
                .foodCategory(foodDetails.getFoodCategory())
                .foodPrice(foodDetails.getFoodPrice())
                .foodId(foodId)
                .build();

        foodEntity = foodRepository.save(foodEntity);
        FoodDto updatedFoodDto = FoodConverter.convertEntityToDto(foodEntity);
        return updatedFoodDto;
    }

    @Override
    public OperationStatusModel deleteFoodItem(String id) throws Exception {
        FoodEntity food = foodRepository.findByFoodId(id);
        OperationStatusModel operationStatusModel;
        if(food == null){
            operationStatusModel = OperationStatusModel.builder()
                    .operationName(RequestOperationName.DELETE.toString())
                    .operationResult(RequestOperationStatus.ERROR.toString())
                    .build();

            throw new Exception("Food not found!!");
        }else{
            operationStatusModel = OperationStatusModel.builder()
                    .operationName(RequestOperationName.DELETE.toString())
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .build();
            foodRepository.delete(food);
        }

        return operationStatusModel;
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> allFoodsEntity = foodRepository.findAllByFoodId();
        List<FoodDto> allFoodsDto = new ArrayList<>();

        for(FoodEntity food : allFoodsEntity){
            FoodDto foodDto = FoodConverter.convertEntityToDto(food);
            allFoodsDto.add(foodDto);
        }
        return allFoodsDto;
    }
}