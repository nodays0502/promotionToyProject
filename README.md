### 사용 기술 스택

- JAVA 8
- Spring Data JPA
- H2

### URL

USER

- POST /api/user : 회원가입
- POST /api/user/{userId}/resign : 회원 상태를 탈퇴로 변경

PRODUCT

- GET /api/product/{userId} : 해당 유저가 조회할 수 있는 품목 조회
- POST /api/product : 상품 등록
- GET /api/product/{productId}/promotionInfo : 해당 상품의 프로모션 가격 및 상품 가격 조회
- DELETE /api/product/{productId} : 해당 상품 삭제

PROMOTION

- POST /api/promotion : 프로모션 등록
- DELETE /api/promotion/{promotiontId} : 해당 프로모션 삭제



---



### 고려사항

- 도메인 서비스
  - CacluteLowestPromotionPriceService
  - 한 애그리거트로 기능을 구현하기 어려워 도메인 서비스를 만들었다.
  - 처음에 Product에 해당 로직을 넣었지만, Product가 해당 책임을 가지고 있는 것이 맞는지를 생각하였습니다.
  - 할인 정책이 변경된다면 (가장 낮은 할인 가격이 0이 될 수 없다는 정책이 될 수 있다라고 바뀔 경우) 할인 정책은 Product와 관련이 없음에도 이 책임이 Product에 있다는 이유로 Product 애그리거트 코드가 변경된다.
  - 도메인 서비스는 도메인 영역에 위치한 도메인 로직을 표현할 때 사용한다.
  - 도메인 서비스 객체를 애그리거트에 주입하지 않음
    (필드로 갖게 하지 마라)
    - 도메인 객체는 필드로 구성된 데이터와 메서드를 이용해 개념적으로 하나의 모델을 표현
    - discountCalculationService필드는 데이터 자체와는 관련이 없다. (DB 저장 대상도 아니다.)
  - 도메인 로직이면서 한 애그리거트에 넣기에 적합하지 않으면 이 두 로직은 도메인 서비스
  - 도메인 서비스는 도메인 로직을 수행하지 응용 로직을 수행하진 않는다. 
    트랜잭션 처리와 같은 로직은 응용 로직이므로 도메인 서비스가 아닌 응용 서비스에서 처리
  - 해당 로직이 애그리거트의 상태를 변경하거나 애그리거트의 상태 값을 계산하는지 검사해 보면 된다. (상태 값을 계산하므로 도메인 서비스)
- 밸류 타입
  - 보다 명확하게 표현하기 위해서 밸류 오브젝트를 사용하였습니다.
  - 밸류 타입은 개념적으로 완전한 하나를 표현할 때 사용
  - 의미를 명확하게 표현하기 위해 밸류타입 사용 
  - Promotion에서 시작 날짜와 끝 날짜를 묶어 Period로 표현하여 기간을 보다 명확하게 표현
  - 돈을 Money로 표현하여 보다 명확하게 표현하였고, 다순 Long타입의 계산을 Money를 사용하여 보다 명확하게 돈 계산을 표현하였습니다.(''정수 타입 연산''이 아니라 ''돈 계산''이라는 의미로 작성 가능)
  - 밸류 타입을 위한 기능을 추가할 수 있다.  
  - 밸류 타입을 불변으로 작성
    - 동일 객체를 참조할 때 필드값을 변경하면 동일 객체를 참조하는 객체에 영향을 받는다.
    - 불변 객체는 참조 투명성과 스레드에 안전한 특징을 가진다.


- 애그리거트 참조를 키로 한 이유

  - 편한 탐색 오용
    - 다른 애그리거트의 상태를 변경 가능해진다. -> 애그리거트 간의 의존 결합도를 높여 변경을 어렵게 만든다.
  - 위의 문제를 해결하기 위해 ID 참조
    - 애그리거트의 경계가 명확해지고 애그리거트 간 물리적 연결을 제거해 모델의 복잡도를 낮춘다.
    - 애그리거트 간의 의존을 제거해 응집도를 높여준다.

- 인터페이스가 없는 이유 (클린 아키텍처로 구현하지 않은 이유)

  - DIP를 활용하면 도메인이 특정 기술에 의존하지 않는 순수한 도메인 모델을 만들 수 있다.
  - DIP의 주 이유는 저수준 구현이 변경되더라도 고수준이 영향을 받지 않도록 하기 위함이다.
  - 이 구조를 가지면 구현 기술을 변경하더라도 도메인이 받는 영향을 최소화할 수 있다.
  - 하지만 사전 과제에서는 리포지토리의 구현 기술이 변경할 일이 없었고, 런타임에 구현체를 변경해야하는 경우도 존재하지 않기 때문에 인터페이스를 만들지 않았습니다. 개인적으로 인터페이스를 만듦으로써 소스 파일이 늘어나 전체적인 구조의 복잡해질 것이라 생각합니다.
  - 인터페이스가 명확하게 필요하기 전까지 응용 서비스에 대한 인터페이스를 작성하는 것이 좋은 선택지는 아니다.
  - 이러한 이유로 저는 인터페이스를 만들지 않았습니다.

- 서비스에 DTO

  - View에 반환할 필요가 없는 데이터까지 Domain객체에 포함되어 Controller까지 넘어옵니다.
  - 컨트롤러에서 도메인으로 변경 후 서비스에 넘겨준다면 도메인 로직이 Controller에 포함됩니다.
  - serivce단에 DTO에 안들어오게 되면, 여러 종류의 컨트롤러에서 해당 서비스를 사용할 수 있다는 점에서 DTO를 Service단에 들어오면 안된다는 의견도 존재한다.
  - Controller에서 DTO를 완벽하게 Domain 객체로 구성한 뒤 Service에 넘겨주려면, 복잡한 경우 Controller가 여러 Service(혹은 Repository)에 의존하게 됩니다.
  - DTO를 컨트롤러까지만 사용하면 여러 컨트롤러가 해당 서비스를 사용할 수 있다. 하지만, 보통의 경우 한 컨트롤러가 한 서비스를 사용한다.

- 응용 서비스의 크기

  - 한 도메인에 관련된 기능을 구현한 코드
    - 한 클래스에 위치하므로 각 기능에서 동일 로직에 대한 중복 제거할 수 있다.
    - 한 서비스 클래스의 코드 크기가 커지면 연관성이 적은 코드가 한 클래스에 함께 위치할 가능성이 높아지고 결과적으로 관련 없는 코드가 뒤섞여 코드를 이해하는데 방해가 된다.
  - 구분되는 기능별로 서비스 클래스를 구현하는 방식
    - 클래스 개수는 많아지지만 한 클래스에 관련 기능을 모두 구현하는 것과 비교해 코드 품질을 일정 수준으로 유지하는데 도움
    - 각 클래스 별로 필요한 의존 객체만 포함하므로 다른 기능을 구현한 코드에 영향을 받지 않는다.

- setter를 사용하지 않은 이유

  - setter에 단순 상태만 변경할 것인지 비즈니스 규칙을 확인하는 코드를 넣을지 애매해진다. 
    (도메인의 의도를 제대로 담지 못한다.)


    - 기본 생성자와 setter를 사용하게 되면 불완전한 상태를 만들 수 있다.


    - setter를 사용하지 않으면 자연스럽게 도메인의 의도가 잘 들어나는 이름을 사용하게 된다.

