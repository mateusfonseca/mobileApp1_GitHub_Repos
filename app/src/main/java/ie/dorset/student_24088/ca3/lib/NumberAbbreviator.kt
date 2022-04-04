package ie.dorset.student_24088.ca3.lib

interface NumberAbbreviator {
    fun abbreviate(number: Long): String {
        val billion = "b"
        val million = "m"
        val thousand = "k"

        val stringNumber = number.toString()
        val numberLength = stringNumber.length

        when {
            numberLength > 9 -> {
                val result = String.format("%.2f", (number.toDouble() / 1000000000))
                return if (result.contains(".0"))
                    result.substring(0, result.indexOf(".0")) + billion
                else {
                    val thisSub = result.substring(0, result.indexOf("."))
                    if (thisSub.length >= 3)
                        thisSub + billion
                    else
                        thisSub + result.substring(
                            result.indexOf("."),
                            result.length - 1
                        ) + billion
                }
            }
            numberLength > 6 -> {
                val result = String.format("%.2f", (number.toDouble() / 1000000))
                return if (result.contains(".0"))
                    result.substring(0, result.indexOf(".0")) + million
                else {
                    val thisSub = result.substring(0, result.indexOf("."))
                    if (thisSub.length >= 3)
                        thisSub + million
                    else
                        thisSub + result.substring(
                            result.indexOf("."),
                            result.length - 1
                        ) + million
                }
            }
            numberLength > 3 -> {
                val result = String.format("%.2f", (number.toDouble() / 1000))
                return if (result.contains(".0"))
                    result.substring(0, result.indexOf(".0")) + thousand
                else {
                    val thisSub = result.substring(0, result.indexOf("."))
                    if (thisSub.length >= 3)
                        thisSub + thousand
                    else
                        thisSub + result.substring(
                            result.indexOf("."),
                            result.length - 1
                        ) + thousand
                }
            }
            else -> {
                return stringNumber
            }
        }
    }
}