import { NextRequest, NextResponse } from "next/server";
export async function GET(request:NextRequest){
 const base=process.env.CMS_API_BASE_URL;
 if(!base)return NextResponse.json({items:[],source:"fallback"});
 const query=request.nextUrl.searchParams.toString();
 try{const response=await fetch(`${base}/api/public/content?${query}`,{headers:{Accept:"application/json"},cache:"no-store"});if(!response.ok)throw new Error(`CMS ${response.status}`);return NextResponse.json({items:await response.json(),source:"cms-api"},{headers:{"Cache-Control":"public, max-age=30, stale-while-revalidate=300"}})}catch{return NextResponse.json({items:[],source:"fallback"},{status:200})}
}
