# HTML

Как правило, приложения работают с данными, и веб-приложение — не исключение. Зачастую для хранения данных используют сервер баз данных, но в простейшем случае для хранения могут использоваться файлы. В этом домашнем задании мы научимся получать данные нужные для работы приложения, и отображать их в браузере, в понятном для пользователя виде, с помощью HTML разметки. Вкратце познакомимся с фреймворком стилей *Bootstrap* и подключим его в наше веб-приложение.

## Ссылки

* [Подключение стилей Bootstrap](https://getbootstrap.com/docs/5.1/getting-started/introduction/#css)
* [Документация по HttpServletResponse](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/http/HttpServletResponse.html)
* [Документация HttpServletRequest](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/http/HttpServletRequest.html)
* [jackson-databind](https://github.com/FasterXML/jackson-databind) — библиотека для парсинга JSON формата для java.
* [JSON to Java Object](https://www.baeldung.com/jackson-object-mapper-tutorial#2-json-to-java-object) - парсинг JSON в Java объект.

## App.java

## Задачи

* Зарегистрируйте в контейнере tomcat сервлет `UsersServlet`, так чтобы он обрабатывал запросы по пути `/users/*`

## servlet/UsersServlet.java

## Задачи

* Изучите содержимое метода `doGet()`, который в зависимости от пути в адресе запроса вызывает нужный обработчик (метод).
* Реализуйте в методе `getUsers()` логику получения списка пользователей из файла *src/main/resources/users.json*. Для парсинга содержимого файла в объект `List` используйте библиотеку *jackson* (нужные классы уже импортированы в файл).
* В методе `showUsers()` получите список пользователей, с помощью ранее созданного метода `getUsers()`, и выведите его в браузер используя HTML. Отобразите список в табличном виде с полями: `id` и `fullName` (состоит из имени пользователя и фамилии разделённых пробелом). Список будет доступен в браузере по пути */users*. `fullName` сделайте ссылкой ведущей на страницу конкретного пользователя */users/\<id\>*.

  ```java
  // Гипотетический пример, показывающий структуру
  users = [
    {
      "id": "4",
      "firstName": "John",
      "lastName": "Doe",
      "email": "johndoe@gmail.com",
    },
    // другие пользователи
  ]
  ```

  ```html
  <!-- Таблица со списком пользователей -->
  <table>
    <tr>
      ...
      <td>4</td>
      <td>
        <a href="/users/4">John Doe</a>
      </td>
      ...
    </tr>
  </table>
  ```

* В методе `showUser()` реализуйте вывод всех полей пользователя. Вывод организуйте как вам удобно (проще всего использовать таблицу). Идентификатор пользователя `id` передаётся в метод третьим аргументом. Если пользователь с переданным идентификатором не существует, сайт должен вернуть ошибку 404 (страница с HTTP кодом 404) и текстом "Not found".

* Для проверки работы приложения запустите сервер, используя команду `make start`. Приложение в браузере будет доступно по адресу *http://localhost:8000*

## Подсказки

* В этом домашнем задании используются многострочные литералы — многострочный текст, обёрнутый в тройные кавычки. Многострочные литералы доступны в JDK версии 15.
* Для возврата ошибки используйте метод [sendError()](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/http/HttpServletResponse.html#sendError-int-) с передачей соответствующей константы класса *HttpServletResponse*.
* Для корректного отображения кириллицы установите правильный тип передаваемого контента и его кодировку с помощью метода [setContentType()](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/ServletResponse.html#setContentType-java.lang.String-).
