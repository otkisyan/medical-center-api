package com.medicalcenter.receptionapi.util;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class BeanCopyUtils {

    public static void copyNonNullProperties(Object source, Object destination, String... ignoreProperties) {
        String[] combinedIgnoreProperties = mergeArrays(ignoreProperties, getNullProperties(source));
        BeanUtils.copyProperties(source, destination, combinedIgnoreProperties);
    }

    private static String[] mergeArrays(String[] arr1, String[] arr2) {
        String[] mergedArray = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, mergedArray, arr1.length, arr2.length);
        return mergedArray;
    }

    private static String[] getNullProperties(Object source) {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        return Arrays.stream(descriptors)
                .filter(descriptor -> {
                    try {
                        return descriptor.getReadMethod().invoke(source) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(PropertyDescriptor::getName)
                .toArray(String[]::new);
    }
}
