**Плагин для работы с терминалами mPos Toucan**
<br>
Тестировался на версии ридера C14
<br>
**Example**
````
 var success = function(result) {
      alert(JSON.stringify(result, undefined, 2));
    };
    var failure = function(result) {
      alert(JSON.stringify(result, undefined, 2));
    };

        
//packagename - алиас вызывающего пакета
//securesode - код безопасности для приложения mPos
//amount - Стоимость в копейках
//description - описание заказа

    cordova.plugins.toucan.pay({
      packagename:"my.package.com.id",
      securesode:"1234",
      amount:100,
      description:"Payment description"

    }, success, failure);
  }
````
**TODO**
<br>
 - Враппер для ionic-native
 - поддержка дополнительных ридеров
 - Поддержка фискализации и отправления чека на email
 - Валидация входных данных на стророне плагина
 - расширение информативности отчетов о действиях внутри плагина
