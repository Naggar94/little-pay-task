package org.example.mapper;

import org.example.entity.MessageEntity;
import org.example.pojo.TransactionDataResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MessageMapper {
    TransactionDataResponse messageEntityToTransactionDataResponse(MessageEntity messageEntity);
}
