import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class System {
    companion object {
        private const val MOVIE_FILE = "movies.json"
        private const val SESSION_FILE = "session.json"
        private var movieStorage: MovieStorage
        private var sessionStorage: SessionStorage
        private val movieSerializer = MovieStorageJSONSerializer()
        private val sessionSerializer = SessionStorageJSONSerializer()
        private val fileManager = FileManager()

        init {
            movieStorage = MovieStorage(mutableListOf())
            sessionStorage = SessionStorage(mutableListOf())
            loadData()
        }

        private fun makeID(prefix: String = ""): String {
            val sdf = SimpleDateFormat("ddMyyyyhhmmss")
            return prefix + sdf.format(Date()).toString()
        }

        fun addMovie(name: String, duration: Int):Boolean{
            val hasName: (Movie) -> Boolean = { it.name == name }
            if (movieStorage.items.any(hasName)) {
                return false
            }

            val id = makeID("mov")
            movieStorage.items.add(Movie(id, name, duration.toLong()))
            saveData()
            return true
        }

        fun addSession(movieId: String, sessionTime: LocalDateTime, cost: Int): Boolean{
            val idx = movieStorage.items.indexOfFirst {it.id == movieId}
            if (idx == -1) {
                return false
            }
            val movieDuration = movieStorage.items[idx].duration
            val sessionEndTime = sessionTime.plusMinutes(movieDuration)

            var sessionIntersects = false

            for (session in sessionStorage.items) {
                if (session.startTime <= sessionTime && session.endTime >= sessionTime) {
                    sessionIntersects = true
                    break
                }
                if (session.startTime <= sessionEndTime && session.endTime >= sessionEndTime) {
                    sessionIntersects = true
                    break
                }
                if (session.startTime >= sessionTime && session.endTime <= sessionEndTime) {
                    sessionIntersects = true
                    break
                }

            }
            if (sessionIntersects) {
                return false
            }
            val newSession = Session(makeID("ses"), movieId, sessionTime, cost)
            newSession.movie = movieStorage.items[idx]
            sessionStorage.items.add(newSession)
            saveData()

            return true
        }

        fun sellTicket(sessionID: String, place: Int): Ticket{
            val idx = sessionStorage.items.indexOfFirst {it.id == sessionID}
            sessionStorage.items[idx].markPlaceAs(place, SeatStatus.SOLD)
            sessionStorage.items[idx].tickets.add(Ticket(makeID("tkt"), place))
            saveData()
            return sessionStorage.items[idx].tickets.last()
        }

        fun getMovies(): List<Movie> {
            return movieStorage.items
        }

        fun returnTicket(ticketId: String): Boolean{
            for (session in sessionStorage.items) {
                val idx = session.tickets.indexOfFirst { it.ID == ticketId }
                if (idx != -1) {
                    if (session.startTime > LocalDateTime.now()) {
                        session.markPlaceAs(session.tickets[idx].place, SeatStatus.FREE)
                        session.tickets.removeAt(idx)
                        saveData()
                        return true
                    } else{
                        return false
                    }
                }
            }
            return false
        }

        fun editMovie(id: String, newName: String): Boolean {
            val idx = movieStorage.items.indexOfFirst {it.id == id}
            val idxName = movieStorage.items.indexOfLast {it.name == newName}
            if (idxName != -1) {
                return false
            }
            movieStorage.items[idx].name = newName
            saveData()
            return true
        }

        fun markCurrentSessionVisitor(ticketId: String): Boolean {
            val idx = sessionStorage.items.indexOfFirst { it.startTime <= LocalDateTime.now() && it.endTime >= LocalDateTime.now() }
            if (idx == -1) {
                return false
            }
            val ticketIdx = sessionStorage.items[idx].tickets.indexOfFirst { it.ID == ticketId }
            if (ticketIdx == -1) {
                return false
            }
            sessionStorage.items[idx].markPlaceAs(sessionStorage.items[idx].tickets[ticketIdx].place, SeatStatus.TAKEN)
            saveData()
            return true
        }

        fun getFutureSessions(): List<Session> {
            val isFutureSession: (Session) -> Boolean = {it.startTime > LocalDateTime.now()}
            val list: MutableList<Session> = mutableListOf()
            for (session in sessionStorage.items) {
                if (isFutureSession(session)){
                    list.add(session)
                }
            }

            return list
        }

        fun getPassedSessions(): List<Session> {
            val isFutureSession: (Session) -> Boolean = {it.endTime < LocalDateTime.now()}
            val list: MutableList<Session> = mutableListOf()
            for (session in sessionStorage.items) {
                if (isFutureSession(session)){
                    list.add(session)
                }
            }
            return list
        }

        private fun saveData(): Boolean{

            var result = fileManager.writeToFile(MOVIE_FILE, MovieStorageJSONSerializer().serialize(movieStorage))
            result = result && fileManager.writeToFile(SESSION_FILE, SessionStorageJSONSerializer().serialize(sessionStorage))
            return result
        }

        private fun loadData() {
            val unserializedMovies: String = try {
                fileManager.readFromFile(MOVIE_FILE)
            } catch (e: Exception){
                String()
            }
            movieStorage = movieSerializer.deserialize(unserializedMovies) ?: movieStorage
            val unserializedSessions: String = try {
                fileManager.readFromFile(SESSION_FILE)
            } catch (e: Exception){
                String()
            }
            sessionStorage = sessionSerializer.deserialize(unserializedSessions) ?: sessionStorage
            for (session in sessionStorage.items) {
                val hasId: (Movie) -> Boolean = { it.id == session.movieId }
                session.movie = movieStorage.items[movieStorage.items.indexOfFirst(hasId)]
            }
        }

        fun removeSession(sessionId: String): Boolean {
            val idx = sessionStorage.items.indexOfFirst { it.id == sessionId}
            if (idx == -1) {
                return false
            }

            if (sessionStorage.items[idx].startTime > LocalDateTime.now())
                return false

            sessionStorage.items.removeAt(idx)
            saveData()
            return true
        }
    }


}