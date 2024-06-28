package objektwerks

final class Dispatcher(emailer: Emailer, store: Store):
  def dispatch(command: Command): Event = ???
