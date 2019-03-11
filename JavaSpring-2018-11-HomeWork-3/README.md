# otus-java-2018-11-VSkurikhin
## Для курса "Разработчик на Spring Framework" в Otus.ru

#### Группа 2018-11
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 3
 * [x] Создан проект Spring Boot.
 * [x] Перенесены и покрыты тестами следующие классы:
 ````
 AnswerImpl, QuestionImpl, QuestionsImpl, AnswerFactoryImpl, QuestionFactoryImpl,
 MessagesServiceImpl, CSVQuestionsReader, ConsoleQuizExecutor, Main, ApplicationConfig.
 ````
 * [x] Перенесены все свойства в application.yml.
 * [x] Заменён баннер для приложения, логи перенаправленны в файл.
 * [x] Тесты перенесены и использован spring-boot-test-starter.
 * Замечание: Это можно добавить в QuizExecutor.run и тогда весь main будет
 * Замечание: CommandLineRunner тут явный перебор )
 * Замечание: Это лишнее. Тут вас CommandLineRunner подвел. Если уберете, как я рекомендовал то все взлетит