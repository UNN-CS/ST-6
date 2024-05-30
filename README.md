# ST-6 Модульное тестирование на Java с использованием jUnit и Maven (2)


![GitHub pull requests](https://img.shields.io/github/issues-pr/UNN-CS/ST-6)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/UNN-CS/ST-6)

Срок выполнения задания:

**по 26.05.24** ![Relative date](https://img.shields.io/date/1716757200)



## Задание №1

В среде **Intellij Idea** создать проект **Maven** по шаблону **quickstart**. Убедиться в появлении веток `java/main` и `java/test` в разделе `src` проекта, а также файла с конфигурацией `pom.xml` в корне проекта.

Добавить в проект код программы **org.st6.Program.java**, содержащий реализацию игры "Крестики-Нолики" с использованием минимаксного алгоритма.

Далее, построить проект и убедиться, что приложение запускается без ошибок и работоспособно.

## Задание №2

Добавить в проект модульные тесты, покрывающие основной функционал классов и методов. В этой работе упор делается не на качество самих тестов, а на их количество и процент покрытия исходного кода.

В **pom.xml** поместить зависимость пакета для генерации отчета по покрытию:

```
<build>
        <plugins>
            <!-- Code Coverage report generation -->
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.7.9</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>generate-code-coverage-report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

```
После запуска тестов найти в разделе **target** проекта ссылку на отчет (`target/site/jacoco/index.html`) и открыть его в браузере.

![](./images/2.png)


В данной работе необходимо добиться покрытия минимум **60%** исходного кода.
Скриншот отчета сохранить в файле `report/coverage.png` 

## Задание №3

Загрузить проект на **GitHub** и убедиться в работоспособности проекта через сценарии **GH Actions**. 


