class Printer {
    companion object {
        fun printPlaces(placesStatus: List<SeatStatus>) {
            println("Free places: ")
            for (i in 0..placesStatus.lastIndex) {
                if (placesStatus[i] == SeatStatus.FREE) {
                    print("${i + 1}, ")
                }
            }
            println()
            println("Sold places: ")
            for (i in 0..placesStatus.lastIndex) {
                if (placesStatus[i] != SeatStatus.FREE) {
                    print("${i + 1}, ")
                }
            }
            println()
            println("Occupied places: ")
            for (i in 0..placesStatus.lastIndex) {
                if (placesStatus[i] == SeatStatus.TAKEN) {
                    print("${i + 1}, ")
                }
            }
            println()
        }

        fun printSessions(items: List<Session>, showPlaces: Boolean = false) {
            if (items.lastIndex == -1) {
                println("There are no sessions yet")
                return
            }
            for (i in 0..items.lastIndex) {
                println("${i + 1}) ${items[i].movie.name}  starts: ${items[i].startTime} ends: ${items[i].endTime} cost per place : ${items[i].cost}")
                if (showPlaces) {
                    printPlaces(items[i].placesStatus)
                }
            }
        }

        fun printMovies(items: List<Movie>) {
            if (items.lastIndex == -1) {
                println("There are no movies yet")
                return
            }
            for (i in 0..items.lastIndex) {
                println("${i + 1}) ${items[i].name} duration: ${items[i].duration} minutes")
            }
        }

        fun printTicket(ticket: Ticket) {
            println("Ticket: ${ticket.id} for place ${ticket.place + 1}")
        }
    }
}