package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

object Serializer:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity](CodecMakerConfig.withDiscriminatorFieldName(None))
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]
  given JsonValueCodec[House] = JsonCodecMaker.make[House]
  given JsonValueCodec[Foundation] = JsonCodecMaker.make[Foundation]
