-- 新增欄位
ALTER TABLE qiyuan.dbo.banner
    ADD url VARCHAR(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

-- 點燈移除 UNIQUE 約束
ALTER TABLE qiyuan.dbo.lantern_purchase
DROP CONSTRAINT UQ__lantern___83AD2A3DAC8D4F3B;

-- 新增老師服務售價
UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"開運諮詢Test","content":"透過專業分析，提供個人化的開運建議與指引。Test","price":500},{"title":"事業建議Test","content":"針對工作與創業方向，給予命理與實務並重的規劃建議。Test","price":800},{"title":"考試運提升Test","content":"運用能量轉化與心理調整，協助考生穩定心神並提升考運。Test","price":650}]'
WHERE code = 'A001';

UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"姻緣分析","content":"根據八字與流年運勢，分析適合的姻緣對象與時間點。","price":720},{"title":"合婚諮詢","content":"以命盤與性格相容度評估婚配適宜性，提升婚姻穩定性。","price":680},{"title":"感情挽回","content":"提供能量指引與實際策略，協助修補破裂的感情關係。","price":850}]'
WHERE code = 'A002';

UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"財位布局","content":"透過風水佈局調整財位，增進財運與金錢流通。","price":800},{"title":"店面風水","content":"針對商業空間規劃風水動線，提升業績與人氣。","price":770},{"title":"招財符製作","content":"依個人運勢與需求，手工製作具有加持力的招財符。","price":690}]'
WHERE code = 'A003';

UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"淨宅儀式","content":"進行專業淨宅儀式，驅除負能量並恢復空間清淨。","price":850},{"title":"安神除煞","content":"透過法器與儀式，安定家宅氣場，化解凶煞。","price":720},{"title":"驅邪祈福","content":"結合宗教與能量技法，祈求平安順利，驅除不良氣場。","price":880}]'
WHERE code = 'A004';

UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"命盤解析","content":"根據生辰八字進行全方位命盤剖析，掌握人生走勢。","price":900},{"title":"心靈對話","content":"透過傾聽與能量交流，協助釐清內心疑惑與情緒。","price":750},{"title":"能量療癒","content":"使用靈性與能量療法，修復身心靈創傷，提升整體頻率。","price":880}]'
WHERE code = 'A005';

UPDATE qiyuan.dbo.master
SET services_json = N'[{"title":"生命靈數","content":"生命的力量","price":600}]'
WHERE code = 'B001';



------------------------------------------------------------


