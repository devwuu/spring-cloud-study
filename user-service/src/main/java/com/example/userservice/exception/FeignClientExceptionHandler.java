package com.example.userservice.exception;

import com.example.userservice.common.ApiExceptionCode;
import com.example.userservice.common.FeignClientExceptionCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignClientExceptionHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if(StringUtils.contains(methodKey, "OrderServiceClient#findByUserId")){
                    return new CommonException(FeignClientExceptionCode.NotFound);
                }
                break;
            default:
                log.error("feign client exception : {}", response.toString());
                return new CommonException(FeignClientExceptionCode.DefaultException);
        }
        return null;
    }


}
