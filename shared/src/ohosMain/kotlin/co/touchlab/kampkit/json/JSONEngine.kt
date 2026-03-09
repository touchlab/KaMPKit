package co.touchlab.kampkit.json

internal object JSONEngine {

    fun parse(jsonStr: String): Any? {
        return JSONTokener(jsonStr).nextValue()
    }

    fun stringify(jsonObject: JSONObject) = commonStringify(jsonObject)

    fun stringify(jsonArray: JSONArray) = commonStringify(jsonArray)

    fun <K, V> getMutableMap(): MutableMap<K, V> = mutableMapOf()

    fun <E> getMutableList(): MutableList<E> = mutableListOf()
}

fun commonStringify(jsonObject: JSONObject): String {
    val stringer = JSONStringer()
    jsonObject.writeTo(stringer)
    return stringer.toString()
}

fun commonStringify(jsonArray: JSONArray): String {
    val stringer = JSONStringer()
    jsonArray.writeTo(stringer)
    return stringer.toString()
}
