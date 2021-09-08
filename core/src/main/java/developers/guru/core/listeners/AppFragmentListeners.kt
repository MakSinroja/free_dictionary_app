package developers.guru.core.listeners

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:39.
 */
interface AppFragmentListeners {
    fun onFragmentAttached()
    fun onFragmentDetached(tag: String)
}