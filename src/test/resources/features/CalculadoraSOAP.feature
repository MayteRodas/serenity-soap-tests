# language: es
# encoding: UTF-8

@soap @calculadora
Característica: Calculadora SOAP - operaciones aritméticas
  Como    equipo de QA
  Quiero  verificar que el servicio SOAP de calculadora responde correctamente
  Para    garantizar la integridad de los cálculos en producción


  # URL en serenity.conf
  Antecedentes:
    Dado el servicio SOAP de calculadora está disponible

  # happy path
  #@smoke
  Esquema del escenario: Sumar dos números enteros con resultado positivo
    Dado envío un POST SOAP con la operación <operacion> y los valores <valorA> y <valorB>
    Entonces el código de respuesta HTTP debe ser <codigoEsperado>
    Entonces el cuerpo de la respuesta XML debe contener el valor <valorEsperado>
    Ejemplos:
      | operacion | valorA | valorB | codigoEsperado | valorEsperado |
      | "Add"     | "10"   | "5"    | "200"          | "15"          |

  # happy path 2
  #@smoke
    Escenario:  Restar dos números donde el resultado es positivo
    Cuando envío un POST SOAP con la operación "Subtract" y los valores "20" y "8"
    Entonces el código de respuesta HTTP debe ser "200"
      Entonces el cuerpo de la respuesta XML debe contener el valor "12"

   # múltiples operaciones con datos parametrizados ───

  #@regresion
  Esquema del escenario: Verificar las cuatro operaciones aritméticas básicas
    Cuando envío un POST SOAP con la operación "<operacion>" y los valores "<valorA>" y "<valorB>"
    Entonces el código de respuesta HTTP debe ser "200"
    Entonces el cuerpo de la respuesta XML debe contener el valor "<resultadoEsperado>"

    # Datos de entrada
    Ejemplos:
      | operacion | valorA | valorB | resultadoEsperado |
      | Add       | 10     | 5      | 15                |
      | Subtract  | 10     | 5      | 5                 |
      | Multiply  | 4      | 3      | 12                |
      | Divide    | 10     | 2      | 5                 |

  # caso de error
  #@negativo
  Esquema del escenario: Enviar un XML SOAP malformado debe retornar un error
    Cuando envío un POST SOAP con un XML inválido al servicio calculadora
    Entonces el código de respuesta HTTP debe ser <codigoEsperado>
    Entonces el cuerpo de la respuesta XML debe contener el valor "soap:Fault"

    Ejemplos:
      | codigoEsperado |
      | "500"          |