Feature: Negative login scenarios
  Verify that login validation works correctly

  Scenario Outline: username or password  field validation
    And user enters username "<username>" and password "<password>"
    Then login button should be "<button_State>" state

    Examples:
      | username | password | button_State |
      | nika     |          | disabled     |
      |          | kokhre   | disabled     |

  Scenario Outline:  clear field validation and wrong characters
    When user changes language to "<language>"
    And user enters username "<username>" and password "<password>"
    And user clears the "<field_to_clear>" field
    Then login button should be "<button_State>" state
    And "<error_field>" error text should be "<error_text>"

    Examples:
      | language | username | password | field_to_clear | error_field | error_text                                             | button_State |
      | ქართული  | nika     |          | username       | username    | აუცილებელი ველი არ არის შევსებული                      | disabled     |
      | ქართული  |          | kokhre   | password       | password    | აუცილებელი ველი არ არის შევსებული                      | disabled     |
      | ქართული  | !        |          |                | username    | მომხმარებლის სახელში გამოყენებულია არასწორი სიმბოლოები | disabled     |
      | English  | nika     |          | username       | username    | Mandatory field is empty                               | disabled     |
      | English  |          | kokhre   | password       | password    | Mandatory field is empty                               | disabled     |
      | English  | ~        |          |                | username    | Wrong symbols                                          | disabled     |
      | Русский  | nika     |          | username       | username    | Необходимое поле не заполнено                          | disabled     |
      | Русский  |          | kokhre   | password       | password    | Необходимое поле не заполнено                          | disabled     |
      | Русский  | !        |          |                | username    | В имени пользователя использованы недопустимые символы | disabled     |


  Scenario Outline: Wrong credential error message when try to login
    When user changes language to "<language>"
    And user enters username "<username>" and password "<password>"
    Then login button should be "<button_State>" state
    And click login button
    Then appear error with text "<error_text>"

    Examples:
      | language | username | password | button_State | error_text                                            |
      | ქართული  | nikoloz     | kokhre   | enabled      | ავტორიზაცია ვერ ხერხდება, შეიყვანეთ მონაცემები სწორად |
      | English  | nikoloz     | kokhre   | enabled      | Authentication failed, enter data correctly           |
      | Русский  | nikoloz     | kokhre   | enabled      | Авторизация не удалась, введите данные правильно      |

