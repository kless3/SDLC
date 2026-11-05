# Оценка и приоритеты функций (учебная модель)

**Приоритеты:** 10 — обязательное в первую очередь; 20 — важное; 30 — желательное; 40 — низкий приоритет; 50 — кандидат на перенос. Оценки в человеко-днях включают код, тесты, ревью и документацию.

| ID | Function | Priority | Middle days P0 | Depends on |
|---|---|---:|---:|---|
| F01 | Project foundation, database, Flyway | 10 | 5 | — |
| F02 | Registration, login and permissions | 10 | 6 | F01 |
| F03 | Personal goals and progress | 10 | 5 | F01, F02 |
| F04 | Habit schedules and daily check-ins | 10 | 6 | F02, F03 |
| F05 | Calendar events and daily view | 10 | 5 | F02 |
| F06 | Personal notes CRUD | 20 | 3 | F02 |
| F07 | Teams, members and invitations | 10 | 7 | F02 |
| F08 | Shared goals and events | 10 | 5 | F03, F05, F07 |
| F09 | Dashboard and progress indicators | 10 | 4 | F03–F08 |
| F10 | Security, audit and operations | 10 | 4 | F01–F09 |
| F11 | Responsive UI, testing and release | 10 | 5 | Все функции |
| **ИТОГО P0** | | | **55** | |

## Оптимизация P0 → P1 без удаления FR-001–FR-014
| Уточнение | Экономия |
|---|---:|
| Server-side MVC + Thymeleaf, без отдельного SPA/JSON API | 4 дня |
| Общие CRUD/DTO/Bean Validation | 3 дня |
| Готовые механизмы Spring Security | 3 дня |
| Маска дней привычек без rule engine | 2 дня |
| Типовая адаптивная сетка, Actuator и интеграционные тесты | 3 дня |
| **Итого экономия** | **15 дней** |

P1 = 55 − 15 = **40 middle-дней**. Ставка middle условно 40 BYN/ч = 320 BYN/день: **12 800 BYN**.

CHG-002 оценивается отдельно: **7 middle-эквивалентных дней** (контракт/права 1; выборка 2; CSV 1; UI 1; тесты/документация 2). План P2 вместо переноса срока предполагает помощь junior 12 дней; подробности в [изменении](change-request-CHG-002.md).
