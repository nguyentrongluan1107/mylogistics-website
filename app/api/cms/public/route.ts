import { NextRequest, NextResponse } from "next/server";
import mockContent from "@/backend/src/main/resources/mock/cms-content.json";

const readMock=(request:NextRequest)=>{
 const type=request.nextUrl.searchParams.get("type");
 const locale=request.nextUrl.searchParams.get("locale")||"en";
 return mockContent
  .filter(item=>item.status==="PUBLISHED")
  .filter(item=>!type||item.type===type)
  .filter(item=>item.locale===locale)
  .sort((a,b)=>a.sortOrder-b.sortOrder);
};

export async function GET(request:NextRequest){
 const base=process.env.CMS_API_BASE_URL;
 if(!base)return NextResponse.json({items:readMock(request),source:"mock-file"});
 const query=request.nextUrl.searchParams.toString();
 try{const response=await fetch(`${base}/api/public/content?${query}`,{headers:{Accept:"application/json"},cache:"no-store"});if(!response.ok)throw new Error(`CMS ${response.status}`);return NextResponse.json({items:await response.json(),source:"cms-api"},{headers:{"Cache-Control":"public, max-age=30, stale-while-revalidate=300"}})}catch{return NextResponse.json({items:readMock(request),source:"mock-file"},{status:200})}
}
