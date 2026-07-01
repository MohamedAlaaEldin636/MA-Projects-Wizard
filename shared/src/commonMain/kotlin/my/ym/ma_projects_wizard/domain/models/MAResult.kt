package my.ym.ma_projects_wizard.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class MAResult<out T> {

	@Serializable
	sealed class Immediate<out T> : MAResult<T>()

	@Serializable
	data class Success<out T>(val value: T) : Immediate<T>()

	@Serializable
	data class Failure(
		@Transient val throwable: Throwable? = null,
		val throwableStackTraceAsString: String? = throwable?.stackTraceToString(),
	) : Immediate<Nothing>()

	@Serializable
	data object Loading : MAResult<Nothing>()

	companion object;

}

fun MAResult.Companion.failure(throwable: Throwable? = null): MAResult.Failure {
	return MAResult.Failure(throwable = throwable)
}

inline fun <reified T> MAResult.Companion.success(value: T): MAResult.Success<T> {
	return MAResult.Success(value = value)
}

inline fun <reified T> Result<T>.toImmediateMAResult(): MAResult.Immediate<T> {
	val value = getOrElse {
		return MAResult.failure(throwable = it)
	}
	return MAResult.success(value = value)
}
/*inline fun <reified T> MAResult.Immediate<T>.toImmediateMAResult(): Result<T> {
	return when (this) {
		is MAResult.Success -> Result.success(value = value)
		is MAResult.Failure -> Result.failure(exception = throwable ?: Throwable("Unknown"))
	}
}*/

inline fun <reified T> MAResult<T>.getSuccessValueOrThrow(): T {
	return when (this) {
		is MAResult.Success -> value
		is MAResult.Failure -> {
			throw throwable ?: Throwable("MAResult -> Unknown null Throwable")
		}
		MAResult.Loading -> {
			throw Throwable("MAResult -> Trying to get value from Loading State")
		}
	}
}
inline fun <reified T> MAResult<T>.getSuccessValueOrNull(): T? {
	return when (this) {
		is MAResult.Success -> value
		MAResult.Loading,
		is MAResult.Failure -> null
	}
}

fun MAResult<*>.getFailureThrowableOrNull(): Throwable? {
	return when (this) {
		MAResult.Loading,
		is MAResult.Success -> null
		is MAResult.Failure -> throwable
	}
}

val <T> MAResult<T>.isSuccess: Boolean inline get() = this is MAResult.Success

inline fun <reified T> Result<T>?.toMAResult(): MAResult<T> {
	if (this == null) return MAResult.Loading
	return toImmediateMAResult()
}

inline fun <reified T> T.toSuccessMAResult(): MAResult.Success<T> {
	return MAResult.success(value = this)
}
