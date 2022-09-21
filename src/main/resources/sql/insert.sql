TRUNCATE position RESTART IDENTITY CASCADE;
INSERT INTO position VALUES
    (1, 'Лаборант', 'Описание должности "лаборант"'),
    (2, 'Старший лаборант', 'Описание должности "старший лаборант"'),
    (3, 'Научный сотрудник', 'Описание должности "научный сотрудник"'),
    (4, 'Старший научный сотрудник', 'Описание должности "старший научный сотрудник"'),
    (5, 'Директор', 'Описание должности "директор"');

TRUNCATE employee RESTART IDENTITY CASCADE;
INSERT INTO employee VALUES
    (1, 1, 'Иванов Иван Иванович', 'ВМК МГУ', 'Караганда', '5 лет', 'в штате', date('2000-06-22')),
    (2, 2, 'Сергеев Сергей Сергеевич', 'ВМК МГУ', 'Караганда', '5 лет', 'в штате', date('2000-06-22'));

TRUNCATE positions_history RESTART IDENTITY CASCADE;
INSERT INTO positions_history VALUES
    (1, 1, 1, date('2022-01-10'), null),
    (2, 1, 2, date('2021-01-10'), date('2022-01-10')),
    (3, 2, 2, date('2022-01-10'), null);

TRUNCATE project RESTART IDENTITY CASCADE;
INSERT INTO project VALUES
    (1, 'Проект1', date('2018-01-10'), null, 'описание проекта1'),
    (2, 'Проект2', date('2019-01-10'), date('2022-01-10'), 'описание проекта2'),
    (3, 'Проект3', date('2020-01-10'), null, 'описание проекта3');

TRUNCATE project_roles RESTART IDENTITY CASCADE;
INSERT INTO project_roles VALUES
    (1, 1, 'руководитель', 1, 1, '', 'описание роли1 в проекте1'),
    (2, 1, 'тестировщик', 1, 1, '', 'описание роли2 в проекте1'),
    (3, 1, 'разработчик', 2, 1, '', 'описание роли3 в проекте1'),
    (4, 2, 'руководитель', 1, 1, '', 'описание роли1 в проекте2'),
    (5, 3, 'руководитель', 1, 1, '', 'описание роли1 в проекте3'),
    (6, 3, 'тестировщик', 1, 0, '', 'описание роли2 в проекте3'),
    (7, 3, 'разработчик', 5, 3, '', 'описание роли3 в проекте3'),
    (8, 3, 'менеджер проекта', 1, 1, '', 'описание роли4 в проекте3');

TRUNCATE employee_role RESTART IDENTITY CASCADE;
INSERT INTO employee_role VALUES
    (1, 1, 1, date('2018-01-10'), null),
    (2, 1, 4, date('2019-01-10'), date('2022-01-10')),
    (3, 1, 7, date('2020-01-10'), null),
    (4, 2, 2, date('2021-01-10'), null),
    (5, 2, 7, date('2019-01-10'), date('2020-01-10')),
    (6, 2, 3, date('2020-01-10'), null);

/*SELECT * FROM project WHERE proj_id IN (SELECT proj_id FROM project_roles WHERE proj_role_id IN (SELECT proj_role_id FROM employee_role WHERE emp_id = 1))
