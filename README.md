Tropical Home
-------------
>Model of a tropical home.

Model
-----
>Home
* Home 1 --> 1 Roof
* Home 1 --> 1..* Room
* Home 1 --> * Driveway | Garage | Pool | Patio | Lawn | Garden

>Color
* Color( red, green, blue )

>Flooring
* Flooring( carpet, concrete, tile, wood ) 1 --> * Color

>Mode
* Mode ( Local, Remote )

>Roof
* Roof( Alluminum, Galvalume, Tile ) 1 --> * Color

>Room
* Room( Kitchen, Bedroom, Den, Study ) 1 --> * Color & Flooring