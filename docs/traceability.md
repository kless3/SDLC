# Матрица трассируемости FocusFlow

SRS ЛР3 → требования ЛР4 → проектное решение → предлагаемая проверка. Никакие тесты не помечены как фактически пройденные.

| Источник SRS/изменения | Требования системы | Модуль | Проверка |
|---|---|---|---|
| FR-001 | SYS-F-001 | Auth | Регистрация, вход |
| FR-002 | SYS-F-003 | Goals | Создание цели |
| FR-003 | SYS-F-004 | Goals | Редактирование/архив, 403/404 |
| FR-004 | SYS-F-005 | Goals | Границы 0–100 |
| FR-005 | SYS-F-006 | Habits | Дни недели |
| FR-006 | SYS-F-007 | Habits | Повторный POST, UNIQUE |
| FR-007 | SYS-F-008 | CalendarNotes | Валидация дат |
| FR-008 | SYS-F-009 | CalendarNotes | Фильтрация дня |
| FR-009 | SYS-F-010 | CalendarNotes | CRUD заметок, чужой доступ |
| FR-010 | SYS-F-011 | Teams | Транзакция команды/OWNER |
| FR-011 | SYS-F-012 | Teams | Приглашение, дубли |
| FR-012 | SYS-F-002, SYS-F-009, SYS-F-013 | Auth, Teams, Goals, CalendarNotes | Изоляция ресурсов |
| FR-013, FR-014 | SYS-F-014 | Dashboard | Сверка агрегатов с БД |
| CHG-002 | SYS-F-015 | Dashboard / CSV | UTF-8, чужой доступ, формулы |
| NFR-PERF-001 | SYS-Q-001 | Все | 95% ≤ 2с при 50 пользователях |
| NFR-PERF-002 | SYS-Q-002 | Dashboard | ≤ 2с при 1000 объектах |
| NFR-AVAIL-001 | SYS-Q-003 | Operations | Uptime ≥ 99% |
| NFR-REL-001 | SYS-Q-004 | Habits, Teams, Goals | Откат транзакции |
| NFR-SEC-001 | SYS-Q-005, SYS-Q-006 | Auth | Аудит BCrypt/HTTPS/CSRF |
| NFR-SEC-002 | SYS-F-002, SYS-Q-006 | Auth, Teams | Проверка прав |
| NFR-UI-001 | SYS-Q-007 | UI | 360–1920 px, клавиатура |
| NFR-PORT-001 | SYS-Q-008 | UI | Браузерная матрица |
| NFR-OBS-001 | SYS-Q-009 | Operations | Логи без содержимого заметок |
| Эксплуатационное | SYS-Q-010 | Operations | Миграции и восстановление |

См. [requirements.md](requirements.md), [SDD.md](SDD.md), [план](planning/plan.md).
