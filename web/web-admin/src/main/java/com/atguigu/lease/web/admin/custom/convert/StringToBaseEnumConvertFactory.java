package com.atguigu.lease.web.admin.custom.convert;

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConvertFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
       return new Converter<String, T>() {
           @Override
           public T convert(String source) {
               T[] enumConstants = targetType.getEnumConstants();
               for (T enumConstant : enumConstants) {
                   if(enumConstant.getCode().equals(Integer.valueOf(source))){
                       return enumConstant;
                   }
               }
               throw new IllegalArgumentException("coed"+source+"不合法");
           }
       };
    }
}
