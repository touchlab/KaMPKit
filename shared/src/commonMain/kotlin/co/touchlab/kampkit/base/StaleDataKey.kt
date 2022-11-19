package co.touchlab.kampkit.base

enum class StaleDataKey(val keyName: String, val msUntilStale: Int){
    BreedStaleData("BreedTimestampKey", ONE_MINUTE_MS * 10);
}

private const val ONE_MINUTE_MS = 60 * 1000
