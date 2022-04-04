package ie.dorset.student_24088.ca3.lib

interface NumberAbbreviator {
    fun abbreviate(number: Long) : String {
        val BILLION = "b"
        val MILLION = "m"
        val THOUSAND = "k"

        val stringNumber = number.toString()
        val numberLength = stringNumber.length

        if (numberLength > 9) {
            var result = String.format("%.2f", (number.toDouble() / 1000000000))
            if (result.contains(".0"))
                return result.substring(0, result.indexOf(".0")) + BILLION
            else {
                val thisSub = result.substring(0, result.indexOf("."))
                if (thisSub.length >= 3)
                    return thisSub + BILLION
                else
                    return thisSub + result.substring(result.indexOf("."), result.length-1) + BILLION
            }
        } else if (numberLength > 6) {
            var result = String.format("%.2f", (number.toDouble() / 1000000))
            if (result.contains(".0"))
                return result.substring(0, result.indexOf(".0")) + MILLION
            else {
                val thisSub = result.substring(0, result.indexOf("."))
                if (thisSub.length >= 3)
                    return thisSub + MILLION
                else
                    return thisSub + result.substring(result.indexOf("."), result.length-1) + MILLION
            }
        } else if (numberLength > 3) {
            var result = String.format("%.2f", (number.toDouble() / 1000))
            if (result.contains(".0"))
                return result.substring(0, result.indexOf(".0")) + THOUSAND
            else {
                val thisSub = result.substring(0, result.indexOf("."))
                if (thisSub.length >= 3)
                    return thisSub + THOUSAND
                else
                    return thisSub + result.substring(result.indexOf("."), result.length-1) + THOUSAND
            }
        } else {
            return stringNumber
        }
    }
}