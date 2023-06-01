마지막 업데이트 2023.06.01

# Riot API 정리


### LOL-CHALLENGES-V1
 - 도전과제
### Champion-Mastery-V4
 - 챔프 숙련도
### Match-V5
 - 전적 검색
   - /lol/match/v5/matches/by-puuid/{puuid}/ids 로 전적검색을 하여, match_id를 찾아냄.
   - /lol/match/v5/matches/{matchId} 로 찾고싶은 match_id의 세부내용을 가져올수있음.

## TODO 
 - 이 저장한 json들을 웹사이트나, 다른 환경에서도 볼수있게 하고싶다.
 - API를 활용하는 다른 방법은 없을까.
   - 현재 활용하고있는 목록 : 챔피언 숙련도, 전적.
   - 더 활용하고 싶은 목록 : 전적 시스템의 세분화 ...
 - 카톡이나, 디스코드 봇으로 전적을 보내거나
 - 바로 스프레드시트로 전송하여 표로 정리.