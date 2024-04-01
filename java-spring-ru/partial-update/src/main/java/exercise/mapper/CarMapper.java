package exercise.mapper;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarDTO;
import exercise.dto.CarUpdateDTO;
import exercise.model.Car;
import org.mapstruct.*;

// BEGIN
@Mapper(
    uses = {JsonNullableMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CarMapper {
    public abstract CarDTO map(Car car);
    public abstract Car map(CarCreateDTO createDTO);
    public abstract Car update(CarUpdateDTO updateDTO, @MappingTarget Car car);
}
// END
