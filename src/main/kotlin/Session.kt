
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
class Session(val id: String, val movieId: String, @Serializable(with = JsonLocalDateTimeSerializer::class) var startTime: LocalDateTime, var cost: Int) {
    val placesStatus: MutableList<SeatStatus> = mutableListOf()

    companion object {
        @kotlinx.serialization.Transient
        private const val TOTAL_SEATS = 10
    }

    init {
        while (placesStatus.size < TOTAL_SEATS){
            placesStatus.add(SeatStatus.FREE)
        }
    }



    @kotlinx.serialization.Transient
    var movie: Movie = Movie("-1", String(), -1)

    val endTime: LocalDateTime  get() {return startTime.plusMinutes(movie.duration)}

    val tickets: MutableList<Ticket> = mutableListOf()



    fun placeIsFree(place: Int): Boolean {
        return place in 0..<Companion.TOTAL_SEATS && placesStatus[place] == SeatStatus.FREE
    }

    fun markPlaceAs(place: Int, status: SeatStatus): Unit {
        if (place in 0..<Companion.TOTAL_SEATS) {
            if (placesStatus[place].ordinal == status.ordinal - 1 || (placesStatus[place] == SeatStatus.SOLD && status == SeatStatus.FREE)) {
                try {
                    placesStatus[place] = status
                } catch (e: Exception){
                    println(e.message)
                }

                return
            }
        }
    }

    fun isFull(): Boolean {
        return tickets.size >= Companion.TOTAL_SEATS
    }

}