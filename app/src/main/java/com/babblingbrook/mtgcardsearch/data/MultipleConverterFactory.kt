package com.babblingbrook.mtgcardsearch.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.reflect.Type

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class XmlResponse

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class JsonResponse

class MultipleConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation.annotationClass.javaObjectType == XmlResponse::class.java) {
                return SimpleXmlConverterFactory.create()
                    .responseBodyConverter(type, annotations, retrofit)
            }
            if (annotation.annotationClass.javaObjectType == JsonResponse::class.java) {
                return GsonConverterFactory.create()
                    .responseBodyConverter(type, annotations, retrofit)
            }
        }
        return GsonConverterFactory.create()
            .responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return GsonConverterFactory.create()
            .requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}