package cafe.checkout

import cats.MonadError

package object checkout {

  type Erroring[F[_]] = MonadError[F, Throwable]
}
