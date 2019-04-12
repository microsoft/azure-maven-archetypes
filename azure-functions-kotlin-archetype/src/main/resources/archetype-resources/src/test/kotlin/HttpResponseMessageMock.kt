package $package

import com.microsoft.azure.functions.*

/**
 * The mock for HttpResponseMessage, can be used in unit tests to verify if the
 * returned response by HTTP trigger function is correct or not.
 */
class HttpResponseMessageMock(private val httpStatus: HttpStatusType, private val headers: Map<String, String>, private val body: String) : HttpResponseMessage {
    private val httpStatusCode: Int

    init {
        this.httpStatusCode = httpStatus.value()
    }

    override fun getStatus(): HttpStatusType {
        return this.httpStatus
    }

    override fun getStatusCode(): Int {
        return httpStatusCode
    }

    override fun getHeader(key: String): String? {
        return this.headers[key]
    }

    override fun getBody(): String {
        return this.body
    }

    class HttpResponseMessageBuilderMock : HttpResponseMessage.Builder {
        private var body: Any? = null
        private var httpStatusCode: Int = 0
        private val headers: MutableMap<String, String>? = null
        private var httpStatus: HttpStatusType? = null

        fun status(status: HttpStatus): HttpResponseMessage.Builder {
            this.httpStatusCode = status.value()
            this.httpStatus = status
            return this
        }

        override fun status(httpStatusType: HttpStatusType): HttpResponseMessage.Builder {
            this.httpStatusCode = httpStatusType.value()
            this.httpStatus = httpStatusType
            return this
        }

        override fun header(key: String, value: String): HttpResponseMessage.Builder {
            this.headers!![key] = value
            return this
        }

        override fun body(body: Any): HttpResponseMessage.Builder {
            this.body = body
            return this
        }

        override fun build(): HttpResponseMessage {
            return HttpResponseMessageMock(this.httpStatus!!, this.headers.orEmpty(), this.body.toString())
        }
    }
}
