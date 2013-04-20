package scipr

class Timer(name: String, startTime: Long = System.currentTimeMillis) {
    def stop() = System.currentTimeMillis - startTime
    def stopString() = name + " took " + stop() + "ms"
}