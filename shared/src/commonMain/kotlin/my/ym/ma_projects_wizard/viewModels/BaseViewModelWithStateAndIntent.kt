package my.ym.ma_projects_wizard.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModelWithStateAndIntent<State : Any, Intent : Any>(
	protected val initialState: State,
)  : ViewModel() {
	
	private val _state = MutableStateFlow(value = initialState)
	val state = _state.asStateFlow()
	
	protected val stateValue: State get() = state.value
	
	abstract fun handleIntent(intent: Intent)
	
	protected open fun updateState(transformation: (State) -> State) {
		_state.value = transformation(stateValue)
	}
	
}
