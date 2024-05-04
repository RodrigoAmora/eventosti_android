package br.com.rodrigoamora.eventosti.repository

class Resource<T>(
    var result: T?,
    val error: Int? = null
)

fun <T> createFailureResource(
    currentResource: Resource<T?>?,
    errorCode: Int
): Resource<T> {
    if (currentResource != null) {
        return Resource(result = currentResource.result, error = errorCode)
    }
    return Resource(result = null, error = errorCode)
}
