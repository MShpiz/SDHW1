
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Application {
    private val mainMenuText: String = """
            1) Sell ticket 
            2) Return ticket 
            3) Change movie info 
            4) Edit session schedule
            5) Check ticket
            6) Show movie library
            7) Show session info
            8) Exit
        """.trimIndent()

    private val sessionMenuText: String = """
            1) Add Session
            2) Remove Passed Session
            3) Exit
        """.trimIndent()

    private val changeMoviesMenuText: String = """
            1) Add Movie
            2) Edit Movie
            3) Exit
        """.trimIndent()

    fun run() {
        var exit = false
        while (!exit) {
            println(mainMenuText)
            var ans: Int

            try {
                ans = readln().toInt()
            } catch (e: NumberFormatException){
                println("Incorrect command")
                continue
            }

            when (ans) {
                1 -> sellTicket()
                2 -> returnTicket()
                3 -> changeMovieInfo()
                4 -> editSessions()
                5 -> checkTicket()
                6 -> showMovieLibrary()
                7 -> showSessionInfo()
                8 -> exit = true
                else -> println("Incorrect Command")
            }
        }
    }

    private fun showSessionInfo() {
        println("Upcoming sessions:")
        Printer.printSessions(System.getFutureSessions())

        println()
        println("Passed sessions:")
        Printer.printSessions(System.getPassedSessions())
        println()
        println("Enter anything to exit")
        readln()
    }

    private fun showMovieLibrary() {
        Printer.printMovies(System.getMovies())
        println()
        println("Enter anything to exit")
        readln()
    }

    private fun checkTicket() {
        println("Enter ticket id")
        if (System.markCurrentSessionVisitor(readln())) {
            println("Visitor with this ticket can come in")
        } else {
            println("This visitor might have come to the wrong session")
        }
    }

    private fun editSessions() {
        var exit = false
        while (!exit) {
            println(sessionMenuText)
            var ans: Int

            try {
                ans = readln().toInt()
            } catch (e: NumberFormatException){
                println("Incorrect command")
                continue
            }

            when (ans) {
                1 -> addSession()
                2 -> removeSession()
                3 -> exit = true
                else -> println("Incorrect Command")
            }
        }
    }

    private fun checkAnswer(ans: Int, maxValue: Int = 1000000): Boolean {
        return ans >= 1 && ans <= maxValue
    }

    private fun askYNQuestion(prompt: String): Boolean {
        print("$prompt (y/n)")
        val ans = readln()
        return  ans.lowercase() == "y" || ans.lowercase() == "yes"
    }

    private fun sellTicket() {
        println("Chose session")
        val sessions: List<Session> = System.getFutureSessions()
        if (sessions.isEmpty()){
            println("There are no upcoming sessions")
            return
        }
        Printer.printSessions(sessions)
        val sessionNumber: Int
        try{
            sessionNumber = readln().toInt()
        } catch (e: NumberFormatException) {
            println("This session is not on the list")
            return
        }

        if (!checkAnswer(sessionNumber, sessions.lastIndex + 1)){
            println("This session is not on the list")
            return
        }

        if (sessions[sessionNumber-1].isFull()) {
            println("Session is full")
            return
        }

        println("Choose place")
        Printer.printPlaces(sessions[sessionNumber-1].placesStatus)
        var place: Int = -1
        var isFree = false
        while (!isFree) {
            println("Enter number of place you want to take")
            try{
                place = readln().toInt()
            } catch (e: NumberFormatException) {
                place = -1
            }

            isFree = sessions[sessionNumber-1].placeIsFree(place - 1)
            if (!isFree) {
                if (!askYNQuestion("this place is already taken\nDo you want to continue?")) {
                    return
                }
            }
        }

        println("Price for this seat is ${sessions[sessionNumber-1].cost}$")

        if (!askYNQuestion("Do you want to proceed?")) {
            println("Failed to sell ticket")
            return
        }

        var cardNumber: Int
        while (true) {
            println("Enter your cash card number: ")
            var isCard = true
            try {
                cardNumber = readln().toInt()
            } catch (e: NumberFormatException){
                println("not a number")
                isCard = false
            }
            if (isCard) {
                break
            } else {
                if (!askYNQuestion("Do you want to proceed?")) {
                    println("Failed to sell Ticket")
                    return
                }
            }
        }
        val ticket: Ticket = System.sellTicket(sessions[sessionNumber-1].id, place - 1)
        println("Ticket sold:")
        Printer.printTicket(ticket)
        return
    }

    private fun returnTicket() {
        println("Enter ticket id")
        val ticketId = readln()
        if (System.returnTicket(ticketId)) println("Returned ticket") else println("This ticket can not be returned")
    }

    private fun addSession() {
        Printer.printMovies(System.getMovies())
        println("Session for which movie you are going to make (enter its number)? (to exit enter any other number)")
        val result: Int
        try {
            result = readln().toInt()
        } catch (e: Exception) {
            println("exiting to menu")
            return
        }
        if (!checkAnswer(result, System.getMovies().lastIndex + 1)){
            println("exiting to menu")
            return
        }

        val currMovie = System.getMovies()[result - 1]

        println("duration of this movie is ${currMovie.duration}")

        println("Enter date and starting time of session (format: HH:mm dd.MM.yyyy)")

        val sessionTime: LocalDateTime
        try{
            sessionTime = LocalDateTime.parse(readln(), DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
        } catch (e: DateTimeParseException) {
            println("could not recognize date")
            return
        }

        println("Enter cost of a ticket: ")
        val cost: Int
        try{
            cost = readln().toInt()
        } catch (e: NumberFormatException) {
            println("not a number")
            return
        }
        if (!checkAnswer(cost)) {
            println(" Can't set price lower than 1")
            return
        }

        if (System.addSession(currMovie.id, sessionTime, cost)) {
            println("Added session successfully")
        } else {
            println("Unable to add this session with out intersection")
        }

    }

    private fun changeMovieInfo() {
        var exit = false
        while (!exit) {
            println(changeMoviesMenuText)
            var ans: Int

            try {
                ans = readln().toInt()
            } catch (e: NumberFormatException){
                println("Incorrect command")
                continue
            }

            when (ans) {
                1 -> addMovie()
                2 -> editMovie()
                3 -> exit = true
                else -> println("Incorrect Command")
            }
        }
    }

    private fun editMovie() {
        Printer.printMovies(System.getMovies())
        if (System.getMovies().isEmpty()) return
        println("Which you are going to edit (enter its number)? (to exit enter any other number)")
        val result: Int
        try {
            result = readln().toInt()
        } catch (e: Exception) {
            println("exiting to menu")
            return
        }
        if (!checkAnswer(result, System.getMovies().lastIndex + 1)){
            println("exiting to menu")
            return
        }
        var newName: String
        println("Enter new movie name or press enter to keep current one (${System.getMovies()[result - 1].name})")
        newName = readln()
        if (newName.isBlank()) {
            newName = System.getMovies()[result - 1].name
        }
        if (System.editMovie(System.getMovies()[result - 1].id, newName)) {
            println("Set new name to movie successfully")
        } else {
            println("Could not change name to $newName because there is already a movie with this name")
        }
    }

    private fun addMovie() {
        println("Enter movie name")
        var name: String = String()
        while (name.isBlank()) {
            name = readln()
            if (name.isBlank()){
                println("Enter not a blank name")
            }
        }

        println("Enter movie duration in minutes")
        var isCorrect = false
        var duration = 0
        while(!isCorrect) {
            try{
                duration = readln().toInt()
            } catch (e: NumberFormatException) {
                isCorrect = false
                duration = -1
                println("Incorrect duration, please try one more time")
                continue
            }

            isCorrect = checkAnswer(duration)
            if (!isCorrect)
                println("Incorrect duration, please try one more time")
        }
        if (System.addMovie(name, duration)) {
            println("Added movie $name to collection")
        } else {
            println("Movie with this name already exists")
        }
    }

    private fun removeSession() {
        Printer.printSessions(System.getPassedSessions())
        if (System.getPassedSessions().isEmpty())  return

        println("Which session do you want to remove (enter its number)? (to exit enter any other number)")

        val result: Int
        try {
            result = readln().toInt()
        } catch (e: Exception) {
            println("exiting to menu")
            return
        }
        if (!checkAnswer(result, System.getMovies().lastIndex + 1) ){
            println("exiting to menu")
            return
        }

        val currSession = System.getPassedSessions()[result - 1]
        if (System.removeSession(currSession.id)) {
            println("Removed session successfully")
        } else {
            println("Can't remove upcoming sessions")
        }

    }
}