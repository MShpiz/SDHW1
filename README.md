# Как пользоваться программой

## Выбор пункта меню
Меню имеет вид
1) пункт 1
2) пункт 2
3) пункт 3
Для выбора пункта меню необходимо ввести номер этого пункта числом (Напиример для выбора пункта 1 надо ввести 1)

## Список меню
**Главное меню**
1) *Sell ticket*  - продажа билета
2) *Return ticket* - возврат билета
3) *Change movie info* - переход в меню изменения информации о фильмах
4) *Edit session schedule* - преход в меню изменения сеансов
5) *Check ticket* - проверить билет посетителя пришедшего на текущий сеанс (отметить посетителя)
6) *Show movie library* - посмотреть список фильмов
7) *Show session info* - посмотреть список сеансов
8) *Exit* - выход из программы

**Меню изменения информации о сеансах**
1) *Add Session* - добавить сеанс
2) *Remove Passed Session* - удалить прошедшие сеансы[^1]
3) *Exit* - выход в главное меню

**Меню изменения информации о фильмах**[^2]
1) *Add Movie* - добавить фильм
2) *Edit Movie* - отредактировать информацию о фильме
3) *Exit* - выход в главное меню

## Функционал
### Продажа билета
1) Выбирете соответствующий пункт из главного меню
2) Выберите номер сеанса сеанса из списка (ввелите номер этого сеанса)
3) Выберете номер места
4) После того как будет выведена цена билета будет задан вопрос "Хотите ли вы продолжить?", для продолжения покупки введите y
5) Введите номер карты
6) При успешной покупке на экран будет выведен билет

### Возврат билета
1) Выбирете соответствующий пункт из главного меню
2) Введите номер билета (он имеет вид tkt123456790)
3) Если сеанс на который куплен данный билет ещё не начался, билет будет возвращён

### Oтметить посетителя
1) Выбирете соответствующий пункт из главного меню
2) Введите номер билета (он имеет вид tkt123456790)
3) Если сейчас идёт сеанс на который куплен данный билет, место этого посетителя будет отмечено как занятое и будет выведено сообщение о приглашении посетителя в зал, иначе будет выведено сообщение о невозможности впустить посетителя в зал [^3]

### Посмотреть список фильмов
1) Выбирете соответствующий пункт из главного меню
2) Чтобы проекратить просмотр списка фильмов нажмите любую кнопку

### Посмотреть список сеансов
1) Выбирете соответствующий пункт из главного меню
2) Чтобы проекратить просмотр списка сеансов нажмите любую кнопку

### Добавить фильм
1) Выбирете соответствующий пункт из меню изменения информации о фильмах
2) Введите название нового фильма 
3) Введите его продолжительность в минутах
4) Фильм будет добавлен только, если в библиотеке нет фильмов с таким же названием и его продоожительность больше или равна 1 минуте

### Oтредактировать информацию о фильме
1) Выбирете соответствующий пункт из меню изменения информации о фильмах
2) Выберете фильм который вы хотите отредактировать
3) Введите новое название фильма или нажмите Enter чтобы оставить старое
4) Если в библиотеке не фильмов с новым названием, фильм будет изменён

### Добавить сеанс
1) Выбирете соответствующий пункт из меню изменения информации о сеансах
2) Выберете фильм из библиотеки
3) Введите дату и время начала сеанса в формате мм:чч дд.ММ.гггг
4) Введите цену билета на этот сеанс
5) Сеанс будет добавлен только, если сеанс начинается в будущем и его возможно добавить без наложений с другими сеансами

### Удалить прошедший сеанс
1) Выбирете соответствующий пункт из меню изменения информации о сеансах
2) Выберете сеанс который вы хотите удалить
3) Сеанс будет удалён


[^1]: Можно удалять только прошедшие сеансы потому, что при удалении остальных сеансов необходимо провести возврат всех проданных билетов без покупателей. В данной программе по тз реализован только возврат билета при участии покупетеля, а про возврат билетов на не состоявшийся сеанс ничеог не сказано, поэтому такого функционала нет.
[^2]: Удаление фильмов не реализованно из-за того, что при удалении фильма из библиотеки необходимо удалить и все сеансы этого фильма, а эта функция не реализована. (также в тз сказано реализовать редактирование информации о фильмах, но не уточнено входит ли уделение фильмов в редактирование (с сеансами аналогично))
[^3]: Посетитель может входить в зал по билету несколько раз

