package developers.guru.core.listeners

/**
 * Created by Maulik V. Sinroja on 2021-09-08 16:56.
 */
interface SnackBarMessagesListeners {
    fun onFailure(message: String)
    fun onSuccess(message: String)
    fun onWarning(message: String)
}