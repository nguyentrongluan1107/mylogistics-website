import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "MyLogistics | Delivering Myanmar Forward",
  description: "Reliable express delivery, international shipping, warehousing and logistics solutions across Myanmar.",
  other: {
    "codex-preview": "development",
  },
  icons: {
    icon: "/mylogistics-favicon.png",
    shortcut: "/mylogistics-favicon.png",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="antialiased">{children}</body>
    </html>
  );
}
