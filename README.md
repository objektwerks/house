Tropical Home
-------------
>A model of a tropical home.

Model
-----
>Home
* Home 1 --> 1 Roof 1 --> 1 Color
* Home 1 --> 1..* Room 1..* --> Flooring
* Home 1 --> * Landscaping
* Home 1 --> * Driveway | Garage | Pool | Patio 1 --> Color

>Color
* Color( red, green, blue )

>Flooring
* Flooring( carpet, concrete, tile, wood ) 1 --> Color

>Mode
* Mode ( InHome, Vacation, Remote )

>Roof
* Roof( Alluminum, Gallvalume, Tile ) 1 --> Color

>Room
* Room( Kitchen, Bedroom, Den, Study ) 1 --
Color