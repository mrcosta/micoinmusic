# micoinmusic

[![Build Status](https://travis-ci.org/mrcosta/micoinmusic.svg?branch=master)](https://travis-ci.org/mrcosta/micoinmusic)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0bb48c0f1c994bd2b5abef5ab007ce60)](https://www.codacy.com/app/mrcosta/micoinmusic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mrcosta/micoinmusic&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/mrcosta/micoinmusic?branch=master)](https://bettercodehub.com/)
[![codecov](https://codecov.io/gh/mrcosta/micoinmusic/branch/master/graph/badge.svg)](https://codecov.io/gh/mrcosta/micoinmusic)

# Request examples:

* get access token: 
https://accounts.spotify.com/authorize/?client_id=2c7dce3390db4d20be2c80ac7c7e4a9a&response_type=token&scope=user-follow-read&redirect_uri=http://localhost:8080/callback&show_dialog=false
* curl -X GET "https://api.spotify.com/v1/me/following?type=artist&limit=50" -H "Accept: application/json" -H "Authorization: Bearer BQB-OKlogIBSBWnhI4naZueesrtRDKmXXgUATFr5TZ8Der91skrBoBjCtmQXo2P6gJzX-yzQCDnMbWVYOW_5XXm_K5y8C5wH4nKRm40PG2IUZ0nM05He1_nWHygKg2FUxPJ0Kpyb8BdPeUARe4v7Dw"


#/v1/artists/{id}/albums	Get an artist's albums
curl -X GET "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI/albums?album_type=album&market=US&limit=50" -H "Accept: application/json" -H "Authorization: Bearer BQBExk-SgQKDR-kaQEzfX_q6tWqhFgVdjFrNfvGk6hKIYD4-Ld10JOfM-4eif7wYHJISCGF36vec5W2u3Jwry6dWwwk_01LYoOB2oRXGdFal-JOm2IpTpiVk5ZL0-Q7D7Mp3TQKm11Xlhkc7934TqBZKlj0cYHaYuEdb4yOzgH4FlCH14e6obhwxMel3drjiFJq7sF0ceiVNNKm39jC5g9jm68edMHZnjeCd05ATgMGXSLxFp0IPEpw3qM-HcfB_ZQgqr8yDeBN_J3poyhm6dEaLRj1NbobS4fbfJmXMTujFdw"

#/v1/albums?ids={ids} Get several albums

# TODOS:

* multiple requests: do the other requests while still loading the followed artists by user (search for its albums and musics)
* compare with last fm (how is the popularity of a song in last fm compared to spotify)
* what is the percentage of scrobbles being doing on spotify considering the number of scrobllers in last fm.
