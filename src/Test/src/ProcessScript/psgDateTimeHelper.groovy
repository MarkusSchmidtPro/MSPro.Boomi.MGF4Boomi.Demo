println "-------------------------------------------------------------------"

import java.text.SimpleDateFormat

final SimpleDateFormat atomDefault = new SimpleDateFormat("yyyyMMdd HHmmss.SSS")
atomDefault.setTimeZone(TimeZone.getTimeZone("CET"))

final SimpleDateFormat utcISOFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
final SimpleDateFormat localISOFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
//utcISOFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

println "Using current (local) today-time..."
String utcDateS = utcISOFormat.format( new Date())
println "Formatted as UTC String    : $utcDateS"

Date dt = utcISOFormat.parse( utcDateS)
println "Formatted as parsed Date   : $dt"
println "-------------------------------------"

String atomTimeS = atomDefault.format( new Date())
println "Formatted as ATOM as String: $atomTimeS"
Date atomTimeUTC = atomDefault.parse( atomTimeS)
println "Formatted as ATOM as UTC Format : $atomTimeUTC"

// it does formatting, only, no time-zone shift!!!
// Take a datetime from UTC time-zone and format it without 'Z'
atomTimeS = localISOFormat.format( atomTimeUTC)
println "ATOM-nowUTC() as String: $atomTimeS"

println "---"

/*String inS = "2021-07-15T00:00:00+0200"
OffsetDateTime odt = OffsetDateTime.parse( inS )

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
ZonedDateTime zonedDateTime = ZonedDateTime.parse(inS, formatter)
println(zonedDateTime)*/

Date today = new Date()
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ")
today = f.parse("2021-07-15T00:00:00+02:00")

println( "Today (local  ): ${f.format( today)}")

f.setTimeZone(TimeZone.getTimeZone("CET"))
println( "Today (UTC=CET): ${f.format( today)}")

today = f.parse("2021-07-15T00:00:00+0200")
println( "Today (UTC=CET): ${f.format( today)}")

                