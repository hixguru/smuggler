package io.mironov.smuggler.compiler.generators

import io.michaelrocks.grip.Grip
import io.michaelrocks.grip.mirrors.signature.GenericType
import java.util.HashMap
import java.util.NoSuchElementException

internal class ValueContext(
    val type: GenericType,
    val grip: Grip
) {
  private val names = HashMap<String, Int>()

  fun typed(newType: GenericType): ValueContext {
    val context = ValueContext(newType, grip)
    val variables = names

    return context.apply {
      variables(variables)
    }
  }

  fun variables(values: Map<String, Int>) {
    names.clear()
    names.putAll(values)
  }

  fun variable(name: String, index: Int) {
    names[name] = index
  }

  fun variable(name: String): Int {
    return names[name] ?: throw NoSuchElementException("Unknown variable \"$name\"")
  }

  fun value(index: Int) = variable("__smuggler__value__", index)
  fun value() = variable("__smuggler__value__")

  fun property(name: String, index: Int) = variable("__smuggler__property__${name}__", index)
  fun property(name: String) = variable("__smuggler__property__${name}__")

  fun parcel(index: Int) = variable("__smuggler__parcel__", index)
  fun parcel(): Int = variable("__smuggler__parcel__")

  fun flags(index: Int) = variable("__smuggler__flags__", index)
  fun flags(): Int = variable("__smuggler__flags__")
}
