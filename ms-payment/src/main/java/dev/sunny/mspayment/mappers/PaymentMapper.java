package dev.sunny.mspayment.mappers;

import dev.sunny.mspayment.dtos.PaymentRequestDTO;
import dev.sunny.mspayment.dtos.PaymentResponseDTO;
import dev.sunny.mspayment.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, uses = {DateTimeMapper.class})
public interface PaymentMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "modifiedOn", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    Payment toPayment(PaymentRequestDTO paymentRequestDTO);

    @Mapping(target = "paymentDate", source = "modifiedOn")
    PaymentResponseDTO toPaymentResponse(Payment payment);

}
