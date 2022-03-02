package w.y.tutorial.error

class NotFoundException(message: String): RuntimeException(message)
class BadRequestException(message: String): RuntimeException(message)