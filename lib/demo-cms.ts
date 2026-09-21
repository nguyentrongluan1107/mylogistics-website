export type DemoContent = {
  id: string;
  type: string;
  title: string;
  slug: string;
  summary: string;
  body?: string;
  imageUrl?: string;
  ctaLabel?: string;
  ctaUrl?: string;
  status: "DRAFT" | "PENDING_REVIEW" | "PUBLISHED" | "ARCHIVED";
  createdBy: string;
  updatedAt: string;
};

const KEY = "mylogistics-demo-cms-v1";

const seed: DemoContent[] = [
  {id:"banner-1",type:"BANNER",title:"Scale your business, we handle the miles.",slug:"scale-your-business",summary:"Save up to 25% on domestic shipping for new business accounts this September.",body:"NEW BUSINESS CAMPAIGN",imageUrl:"/mylogistics-hero.png",ctaLabel:"Register for the offer",ctaUrl:"#contact",status:"PUBLISHED",createdBy:"Thiri Aye",updatedAt:"2026-09-15T09:42:00Z"},
  {id:"banner-2",type:"BANNER",title:"Yangon to Mandalay in just 24 hours.",slug:"yangon-mandalay-express",summary:"A faster express lane for urgent documents and parcels, with end-to-end tracking.",body:"EXPRESS DELIVERY",imageUrl:"/mylogistics-hero.png",ctaLabel:"Explore express delivery",ctaUrl:"#services",status:"PUBLISHED",createdBy:"Min Khant",updatedAt:"2026-09-14T16:18:00Z"},
  {id:"banner-3",type:"BANNER",title:"Myanmar to the world, made simpler.",slug:"myanmar-to-the-world",summary:"Reliable cross-border delivery, customs support and transparent shipment visibility.",body:"INTERNATIONAL SHIPPING",imageUrl:"/mylogistics-hero.png",ctaLabel:"Get a shipping quote",ctaUrl:"#contact",status:"PUBLISHED",createdBy:"Su Myat",updatedAt:"2026-09-14T11:05:00Z"},
  {id:"news-1",type:"NEWS",title:"New service points in Shan State",slug:"new-service-points-shan",summary:"More convenient drop-off and collection locations.",status:"PENDING_REVIEW",createdBy:"Su Myat",updatedAt:"2026-09-14T11:05:00Z"},
  {id:"career-1",type:"CAREER",title:"Operations Supervisor — Mandalay",slug:"operations-supervisor-mandalay",summary:"Join our Mandalay operations team.",status:"DRAFT",createdBy:"HR Team",updatedAt:"2026-09-13T14:30:00Z"},
  {id:"article-1",type:"NEWS",title:"International shipping guide 2026",slug:"international-shipping-guide-2026",summary:"A practical guide for cross-border shipments.",status:"PUBLISHED",createdBy:"Nyein Chan",updatedAt:"2026-09-12T10:12:00Z"},
];

export function readDemoContent(): DemoContent[] {
  if (typeof window === "undefined") return seed;
  const stored = window.localStorage.getItem(KEY);
  if (stored) {
    try { return JSON.parse(stored); } catch { /* reseed below */ }
  }
  window.localStorage.setItem(KEY, JSON.stringify(seed));
  return seed;
}

export function writeDemoContent(items: DemoContent[]) {
  window.localStorage.setItem(KEY, JSON.stringify(items));
  window.dispatchEvent(new CustomEvent("mylogistics-cms-updated"));
}

export function resetDemoContent() {
  writeDemoContent(seed);
  return seed;
}
