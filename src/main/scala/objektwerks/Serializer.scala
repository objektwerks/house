package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

object Serializer:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity](CodecMakerConfig.withDiscriminatorFieldName(None))

  given JsonValueCodec[Command] = JsonCodecMaker.make[Command](CodecMakerConfig.withDiscriminatorFieldName(None))

  given JsonValueCodec[Event] = JsonCodecMaker.make[Event](CodecMakerConfig.withDiscriminatorFieldName(None))
