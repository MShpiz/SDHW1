class Printer {
    companion object{
        fun printPlaces(placesStatus: List<SeatStatus>) {
            println("Free places: ")
            for (i in 0..placesStatus.lastIndex){
                if (placesStatus[i] == SeatStatus.FREE) {
                    print("${i+1}, ")
                }
            }
            println()
            println("Sold places: ")
            for (i in 0..placesStatus.lastIndex){
                if (placesStatus[i] == SeatStatus.SOLD) {
                    print("${i+1}, ")
                }
            }
            println()
            println("Busy places: ")
            for (i in 0..placesStatus.lastIndex){
                if (placesStatus[i] == SeatStatus.TAKEN) {
                    print("${i+1}, ")
                }
            }
            println()
        }

        fun printSessions(items: List<Session>) {
            if (items.lastIndex == -1) {
                println("There are no sessions yet")
                return
            }
            for (i in 0..items.lastIndex) {
                println("${i+1}) ${items[i].movie.name}  starts: ${items[i].startTime} ends: ${items[i].endTime} cost per place : ${items[i].cost}")
                printPlaces(items[i].placesStatus)
            }
        }

        fun  printMovies(items: List<Movie>) {
            if (items.lastIndex == -1) {
                println("There are no movies yet")
                return
            }
            for (i in 0..items.lastIndex) {
                println("${i+1}) ${items[i].name} duration: ${items[i].duration}")
            }
        }

        fun printTicket(ticket: Ticket) {
            println("Ticket: ${ticket.ID} for place ${ticket.place}")
        }
    }
}