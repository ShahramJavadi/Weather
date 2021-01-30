package hsj.shahram.weather.data.model

enum class TimeOfDay(var hour: IntRange) {

    DAWN(0..6),
    MORNING(7..11),
    NOON(12..16),
    EVENING(17..19),
    NIGHT(20..23);




}